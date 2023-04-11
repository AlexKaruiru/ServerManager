import { ChangeDetectionStrategy } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { NotifierService } from 'angular-notifier';
import { BehaviorSubject, catchError, of } from 'rxjs';
import { startWith } from 'rxjs';
import { map, Observable } from 'rxjs';
import { DataState } from './enum/data-status.enum';
import { Status } from './enum/status.enum';
import { AppState } from './interface/app-state';
import { CustomResponse } from './interface/custom-response';
import { Server } from './interface/server';
import { NotificationService } from './service/notification.service';
import { ServerService } from './service/server.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AppComponent implements OnInit{
  appState$: Observable<AppState<CustomResponse>>
  readonly DataState = DataState;
  readonly Status = Status;
  private filterSubject = new BehaviorSubject<string>('');
  private dataSubject = new BehaviorSubject<CustomResponse>(null);
  filterStatus$ = this.filterSubject.asObservable();
  private isLoading = new BehaviorSubject<boolean>(false);
  isLoading$ = this.isLoading.asObservable();
  //private readonly notifier: NotifierService;

  constructor(private serverService: ServerService, private notifier: NotificationService) { }

  
  ngOnInit(): void {
    this.appState$ = this.serverService.servers$
    .pipe(
      map(response => {
        this.notifier.onDefault(response.message);
        this.dataSubject.next(response);
       // this.notifier.notify('success', 'You are awesome! I mean it!');
        return {dataState: DataState.LOADED_STATE, appData: { ...response, data: { servers: response.data.servers.reverse() }} }
      }),
      startWith({dataState: DataState.LOADING_STATE}),
      catchError((error: string) => {
        this.notifier.onError(error);
        return of({dataState: DataState.ERROR_STATE, error})
      })
    );
  }


  pingServer(ipAddress: string): void {
    this.filterSubject.next(ipAddress);
    this.appState$ = this.serverService.ping$(ipAddress)
    .pipe(
      map(response => {
        const index = this.dataSubject.value.data.servers.findIndex( server => server.id === response.data.server.id);
          this.dataSubject.value.data.servers[index] = response.data.server;
          this.notifier.onDefault(response.message);
          this.filterSubject.next('');
        return {dataState: DataState.LOADED_STATE, appData: this.dataSubject.value}
      }),
      startWith({dataState: DataState.LOADED_STATE, appData: this.dataSubject.value}),
      catchError((error: string) => {
        this.filterSubject.next('');
        this.notifier.onError(error);
        return of({dataState: DataState.ERROR_STATE, error})
      })
    );
  }


  saveServer(serverForm: NgForm): void {
    this.isLoading.next(true);
    this.appState$ = this.serverService.save$(serverForm.value as Server)  //as Server not necessary just showing where we are getting the data from.
    .pipe(
      map(response => {
       this.dataSubject.next(
        { ...response, data: {servers: [response.data.server, ...this.dataSubject.value.data.servers] } }
       );
       this.notifier.onDefault(response.message);
       document.getElementById( 'closeModal').click();
       this.isLoading.next(false)
       serverForm.resetForm( { status: this.Status.SERVER_DOWN });
        return {dataState: DataState.LOADED_STATE, appData: this.dataSubject.value}
      }),
      startWith({dataState: DataState.LOADED_STATE, appData: this.dataSubject.value}),
      catchError((error: string) => {
        this.isLoading.next(false);
        this.notifier.onError(error);
        return of({dataState: DataState.ERROR_STATE, error})
      })
    );
  }


  filterServers(status: Status): void {
     this.appState$ = this.serverService.filter$(status, this.dataSubject.value)
    .pipe(
      map(response => {
        this.notifier.onDefault(response.message);
        return {dataState: DataState.LOADED_STATE, appData: response}
      }),
      startWith({dataState: DataState.LOADED_STATE, appData: this.dataSubject.value}),
      catchError((error: string) => {
        this.notifier.onError(error);
        return of({dataState: DataState.ERROR_STATE, error});
      })
    );
  }


deleteServer(server: Server): void {
     this.appState$ = this.serverService.delete$(server.id)
    .pipe(
      map(response => {
          this.dataSubject.next(
            { ...response, data:
            { servers: this.dataSubject.value.data.servers.filter (s => s.id !==server.id) } } 
          );
          this.notifier.onDefault(response.message);
        return {dataState: DataState.LOADED_STATE, appData: this.dataSubject.value}
      }),
      startWith({dataState: DataState.LOADED_STATE, appData: this.dataSubject.value}),
      catchError((error: string) => {
        this.notifier.onError(error);
        return of({dataState: DataState.ERROR_STATE, error})
      })
    );
  }

  printReport(): void {
    this.notifier.onDefault("Report Downloaded");
   // window.print(); // returns a pdf. however you have to comment the rest of the method. You can also implement both functionalities using a drop down in the button.
    let dataType = 'application/vnd.ms-excel.sheet.macroEnabled.12';
    let tableSelect = document.getElementById( 'servers');
    let tableHtml = tableSelect.outerHTML.replace (/ /g, '%20');
    let downloadLink = document.createElement( 'a');
    document.body.appendChild(downloadLink);
    downloadLink.href = 'data:' + dataType + ' , ' + tableHtml;
    downloadLink.download = 'server-report.xls';
    downloadLink.click();
    document.body.removeChild(downloadLink);
    
  }
  

}
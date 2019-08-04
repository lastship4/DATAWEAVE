import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class WsService {

  //url = 'https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22';
  url1 = 'http://localhost:8102/data/view/fulltext?text=';
  //url1 = 'http://localhost:8102/data/view/name/AWxYQ1pCdt11LK6umm37';
  constructor(private _service: HttpClient) { }

  getWeather(input_value) {
    const httpGetOptions = {
        withCredentials: true,
    };
    /*this._service.get(this.url1+input_value).subscribe(data => {
      console.log('data: ' + data);
    });*/

    return this._service.get(this.url1+input_value);
  }
}

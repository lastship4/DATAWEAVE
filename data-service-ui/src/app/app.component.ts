import { Component } from '@angular/core';
import { WsService } from './ws.service';
import { OnInit } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'data-service-ui';
  report = new Object;
  input_value = '';
  product = null;
  constructor(private ws: WsService) {}

  ngOnInit() {
    
  }

  search(input_value){
    this.ws.getWeather(input_value).subscribe(data => {
      this.report = data;
    });
  }

  cacheProduct(product){
    this.product = product;
  }



}

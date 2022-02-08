import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';
import { UserDataService } from '../_services/user-data.service';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  content: string;

  constructor(private userService: UserService, private userData: UserDataService) { }

  ngOnInit(): void {

    this.userData.getPublicContent().subscribe(data=>{
      console.log(data);
    });

    this.userService.getPublicContent().subscribe(
      data => {
        this.content = data;
      },
      err => {
        this.content = JSON.parse(err.error).message;
      }
    );
  }

}

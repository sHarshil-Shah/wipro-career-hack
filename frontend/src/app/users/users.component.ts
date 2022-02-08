import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserDataService } from '../_services/user-data.service';
@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

ERROR:String="";
  users: Array<any>;
  constructor(private userData: UserDataService, private router: Router) { }



  ngOnInit(): void {

    this.userData.getPublicContent().subscribe(data => {
      // console.log(data);

      this.users = data;
    });
  }

  register() {
    this.router.navigate(['/register']);
  }

  delete(id) {
    this.userData.delete(id).subscribe(data => {
        window.location.reload();
    },err=>{
      this.ERROR = err.error.message;
    });

  }
}

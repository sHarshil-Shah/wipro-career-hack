import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { UserDataService } from 'src/app/_services/user-data.service';

@Component({
  selector: 'app-password',
  templateUrl: './password.component.html',
  styleUrls: ['./password.component.css']
})
export class PasswordComponent implements OnInit {
  id: Number;

  private routeSub: Subscription;
  constructor(private userService: UserDataService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.routeSub = this.route.params.subscribe(params => {
      // console.log(params) //log the entire params object
      this.id = params['id'];
      //log the value of id
    });
    // const id = Number(this.route.snapshot.paramMap.get('id'));
    // this.userService.getUser(id)
    //   .subscribe(user => {
    //     console.log(user);
    //     this.user = user;
    //   });


  }

  ngOnDestroy() {
    this.routeSub.unsubscribe();
  }
  form: any = {};
  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';
  onSubmit(): void {
    // console.log(this.form);
    this.userService.setPassword(this.form, this.id).subscribe(
      data => {
        console.log(data);
        this.router.navigate(['/login']);
        // console.log(data);
        // this.isSuccessful = true;
        // this.isSignUpFailed = false;
      },
      err => {
        console.log(err);
        if (!err.error.status) {
          this.errorMessage = err.error.message;
          this.isSignUpFailed = true;
        } else {
          this.isSuccessful = true;
          this.isSignUpFailed = false;
        }

        this.router.navigate(['/login']);
      }
    );
  }

}

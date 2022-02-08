import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Globals } from '../globals';

const API_URL = Globals.BACKEND_URL + 'api/users/';

@Injectable({
  providedIn: 'root'
})
export class UserDataService {
  constructor(private http: HttpClient) { }

  getPublicContent(): Observable<any> {
    return this.http.get(API_URL + 'all', { responseType: 'json' });
  }

  delete(id): Observable<any> {
    return this.http.delete(API_URL + 'delete/' + id, { responseType: 'json' });
  }

  getUser(id): Observable<any> {
    return this.http.get(API_URL + id, { responseType: 'json' });
  }

  setPassword(form, id): Observable<any> {
    return this.http.put(API_URL + 'update/' + id, {
      password: form.password
    }, { responseType: 'json' });
  }

}



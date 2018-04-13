import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class BudgetService {

  constructor(private http: HttpClient) {
  }

}

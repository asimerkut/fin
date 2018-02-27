import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { PerPerson } from './per-person.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<PerPerson>;

@Injectable()
export class PerPersonService {

    private resourceUrl =  SERVER_API_URL + 'api/per-people';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/per-people';

    constructor(private http: HttpClient) { }

    create(perPerson: PerPerson): Observable<EntityResponseType> {
        const copy = this.convert(perPerson);
        return this.http.post<PerPerson>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(perPerson: PerPerson): Observable<EntityResponseType> {
        const copy = this.convert(perPerson);
        return this.http.put<PerPerson>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<PerPerson>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<PerPerson[]>> {
        const options = createRequestOption(req);
        return this.http.get<PerPerson[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PerPerson[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<PerPerson[]>> {
        const options = createRequestOption(req);
        return this.http.get<PerPerson[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PerPerson[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: PerPerson = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<PerPerson[]>): HttpResponse<PerPerson[]> {
        const jsonResponse: PerPerson[] = res.body;
        const body: PerPerson[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to PerPerson.
     */
    private convertItemFromServer(perPerson: PerPerson): PerPerson {
        const copy: PerPerson = Object.assign({}, perPerson);
        return copy;
    }

    /**
     * Convert a PerPerson to a JSON which can be sent to the server.
     */
    private convert(perPerson: PerPerson): PerPerson {
        const copy: PerPerson = Object.assign({}, perPerson);
        return copy;
    }
}

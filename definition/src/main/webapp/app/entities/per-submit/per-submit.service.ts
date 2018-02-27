import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { PerSubmit } from './per-submit.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<PerSubmit>;

@Injectable()
export class PerSubmitService {

    private resourceUrl =  SERVER_API_URL + 'api/per-submits';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/per-submits';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(perSubmit: PerSubmit): Observable<EntityResponseType> {
        const copy = this.convert(perSubmit);
        return this.http.post<PerSubmit>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(perSubmit: PerSubmit): Observable<EntityResponseType> {
        const copy = this.convert(perSubmit);
        return this.http.put<PerSubmit>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<PerSubmit>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<PerSubmit[]>> {
        const options = createRequestOption(req);
        return this.http.get<PerSubmit[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PerSubmit[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<PerSubmit[]>> {
        const options = createRequestOption(req);
        return this.http.get<PerSubmit[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PerSubmit[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: PerSubmit = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<PerSubmit[]>): HttpResponse<PerSubmit[]> {
        const jsonResponse: PerSubmit[] = res.body;
        const body: PerSubmit[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to PerSubmit.
     */
    private convertItemFromServer(perSubmit: PerSubmit): PerSubmit {
        const copy: PerSubmit = Object.assign({}, perSubmit);
        copy.submitDate = this.dateUtils
            .convertLocalDateFromServer(perSubmit.submitDate);
        return copy;
    }

    /**
     * Convert a PerSubmit to a JSON which can be sent to the server.
     */
    private convert(perSubmit: PerSubmit): PerSubmit {
        const copy: PerSubmit = Object.assign({}, perSubmit);
        copy.submitDate = this.dateUtils
            .convertLocalDateToServer(perSubmit.submitDate);
        return copy;
    }
}

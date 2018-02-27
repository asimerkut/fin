import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { PerExcuse } from './per-excuse.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<PerExcuse>;

@Injectable()
export class PerExcuseService {

    private resourceUrl =  SERVER_API_URL + 'api/per-excuses';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/per-excuses';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(perExcuse: PerExcuse): Observable<EntityResponseType> {
        const copy = this.convert(perExcuse);
        return this.http.post<PerExcuse>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(perExcuse: PerExcuse): Observable<EntityResponseType> {
        const copy = this.convert(perExcuse);
        return this.http.put<PerExcuse>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<PerExcuse>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<PerExcuse[]>> {
        const options = createRequestOption(req);
        return this.http.get<PerExcuse[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PerExcuse[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<PerExcuse[]>> {
        const options = createRequestOption(req);
        return this.http.get<PerExcuse[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PerExcuse[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: PerExcuse = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<PerExcuse[]>): HttpResponse<PerExcuse[]> {
        const jsonResponse: PerExcuse[] = res.body;
        const body: PerExcuse[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to PerExcuse.
     */
    private convertItemFromServer(perExcuse: PerExcuse): PerExcuse {
        const copy: PerExcuse = Object.assign({}, perExcuse);
        copy.startDate = this.dateUtils
            .convertLocalDateFromServer(perExcuse.startDate);
        copy.finishDate = this.dateUtils
            .convertLocalDateFromServer(perExcuse.finishDate);
        return copy;
    }

    /**
     * Convert a PerExcuse to a JSON which can be sent to the server.
     */
    private convert(perExcuse: PerExcuse): PerExcuse {
        const copy: PerExcuse = Object.assign({}, perExcuse);
        copy.startDate = this.dateUtils
            .convertLocalDateToServer(perExcuse.startDate);
        copy.finishDate = this.dateUtils
            .convertLocalDateToServer(perExcuse.finishDate);
        return copy;
    }
}

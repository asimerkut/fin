import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { DefPivot } from './def-pivot.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<DefPivot>;

@Injectable()
export class DefPivotService {

    private resourcePivotData =  SERVER_API_URL + 'api/def-pivot-data';

    private resourceUrl =  SERVER_API_URL + 'api/def-pivots';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/def-pivots';

    constructor(private http: HttpClient) { }

    getPivotData(id: number): Observable<EntityResponseType> {
        return this.http.get(`${this.resourcePivotData}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    create(defPivot: DefPivot): Observable<EntityResponseType> {
        const copy = this.convert(defPivot);
        return this.http.post<DefPivot>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(defPivot: DefPivot): Observable<EntityResponseType> {
        const copy = this.convert(defPivot);
        return this.http.put<DefPivot>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<DefPivot>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<DefPivot[]>> {
        const options = createRequestOption(req);
        return this.http.get<DefPivot[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DefPivot[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<DefPivot[]>> {
        const options = createRequestOption(req);
        return this.http.get<DefPivot[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DefPivot[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: DefPivot = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<DefPivot[]>): HttpResponse<DefPivot[]> {
        const jsonResponse: DefPivot[] = res.body;
        const body: DefPivot[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to DefPivot.
     */
    private convertItemFromServer(defPivot: DefPivot): DefPivot {
        const copy: DefPivot = Object.assign({}, defPivot);
        return copy;
    }

    /**
     * Convert a DefPivot to a JSON which can be sent to the server.
     */
    private convert(defPivot: DefPivot): DefPivot {
        const copy: DefPivot = Object.assign({}, defPivot);
        return copy;
    }
}

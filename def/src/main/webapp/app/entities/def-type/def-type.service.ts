import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { DefType } from './def-type.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<DefType>;

@Injectable()
export class DefTypeService {

    private resourceUrl =  SERVER_API_URL + 'api/def-types';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/def-types';

    constructor(private http: HttpClient) { }

    create(defType: DefType): Observable<EntityResponseType> {
        const copy = this.convert(defType);
        return this.http.post<DefType>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(defType: DefType): Observable<EntityResponseType> {
        const copy = this.convert(defType);
        return this.http.put<DefType>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<DefType>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<DefType[]>> {
        const options = createRequestOption(req);
        return this.http.get<DefType[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DefType[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<DefType[]>> {
        const options = createRequestOption(req);
        return this.http.get<DefType[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DefType[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: DefType = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<DefType[]>): HttpResponse<DefType[]> {
        const jsonResponse: DefType[] = res.body;
        const body: DefType[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to DefType.
     */
    private convertItemFromServer(defType: DefType): DefType {
        const copy: DefType = Object.assign({}, defType);
        return copy;
    }

    /**
     * Convert a DefType to a JSON which can be sent to the server.
     */
    private convert(defType: DefType): DefType {
        const copy: DefType = Object.assign({}, defType);
        return copy;
    }
}

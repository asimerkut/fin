import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { DefRelation } from './def-relation.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<DefRelation>;

@Injectable()
export class DefRelationService {

    private resourceUrl =  SERVER_API_URL + 'api/def-relations';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/def-relations';

    constructor(private http: HttpClient) { }

    create(defRelation: DefRelation): Observable<EntityResponseType> {
        const copy = this.convert(defRelation);
        return this.http.post<DefRelation>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(defRelation: DefRelation): Observable<EntityResponseType> {
        const copy = this.convert(defRelation);
        return this.http.put<DefRelation>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<DefRelation>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<DefRelation[]>> {
        const options = createRequestOption(req);
        return this.http.get<DefRelation[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DefRelation[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<DefRelation[]>> {
        const options = createRequestOption(req);
        return this.http.get<DefRelation[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DefRelation[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: DefRelation = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<DefRelation[]>): HttpResponse<DefRelation[]> {
        const jsonResponse: DefRelation[] = res.body;
        const body: DefRelation[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to DefRelation.
     */
    private convertItemFromServer(defRelation: DefRelation): DefRelation {
        const copy: DefRelation = Object.assign({}, defRelation);
        return copy;
    }

    /**
     * Convert a DefRelation to a JSON which can be sent to the server.
     */
    private convert(defRelation: DefRelation): DefRelation {
        const copy: DefRelation = Object.assign({}, defRelation);
        return copy;
    }
}

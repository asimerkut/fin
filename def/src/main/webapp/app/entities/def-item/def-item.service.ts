import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { DefItem } from './def-item.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<DefItem>;

@Injectable()
export class DefItemService {

    private resourceUrl =  SERVER_API_URL + 'api/def-items';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/def-items';

    constructor(private http: HttpClient) { }

    create(defItem: DefItem): Observable<EntityResponseType> {
        const copy = this.convert(defItem);
        return this.http.post<DefItem>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(defItem: DefItem): Observable<EntityResponseType> {
        const copy = this.convert(defItem);
        return this.http.put<DefItem>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<DefItem>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<DefItem[]>> {
        const options = createRequestOption(req);
        return this.http.get<DefItem[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DefItem[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<DefItem[]>> {
        const options = createRequestOption(req);
        return this.http.get<DefItem[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DefItem[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: DefItem = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<DefItem[]>): HttpResponse<DefItem[]> {
        const jsonResponse: DefItem[] = res.body;
        const body: DefItem[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to DefItem.
     */
    private convertItemFromServer(defItem: DefItem): DefItem {
        const copy: DefItem = Object.assign({}, defItem);
        return copy;
    }

    /**
     * Convert a DefItem to a JSON which can be sent to the server.
     */
    private convert(defItem: DefItem): DefItem {
        const copy: DefItem = Object.assign({}, defItem);
        return copy;
    }
}

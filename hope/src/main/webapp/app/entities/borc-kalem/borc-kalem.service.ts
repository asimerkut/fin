import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { BorcKalem } from './borc-kalem.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<BorcKalem>;

@Injectable()
export class BorcKalemService {

    private resourceUrl =  SERVER_API_URL + 'api/borc-kalems';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/borc-kalems';

    constructor(private http: HttpClient) { }

    create(borcKalem: BorcKalem): Observable<EntityResponseType> {
        const copy = this.convert(borcKalem);
        return this.http.post<BorcKalem>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(borcKalem: BorcKalem): Observable<EntityResponseType> {
        const copy = this.convert(borcKalem);
        return this.http.put<BorcKalem>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<BorcKalem>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<BorcKalem[]>> {
        const options = createRequestOption(req);
        return this.http.get<BorcKalem[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<BorcKalem[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<BorcKalem[]>> {
        const options = createRequestOption(req);
        return this.http.get<BorcKalem[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<BorcKalem[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: BorcKalem = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<BorcKalem[]>): HttpResponse<BorcKalem[]> {
        const jsonResponse: BorcKalem[] = res.body;
        const body: BorcKalem[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to BorcKalem.
     */
    private convertItemFromServer(borcKalem: BorcKalem): BorcKalem {
        const copy: BorcKalem = Object.assign({}, borcKalem);
        return copy;
    }

    /**
     * Convert a BorcKalem to a JSON which can be sent to the server.
     */
    private convert(borcKalem: BorcKalem): BorcKalem {
        const copy: BorcKalem = Object.assign({}, borcKalem);
        return copy;
    }
}

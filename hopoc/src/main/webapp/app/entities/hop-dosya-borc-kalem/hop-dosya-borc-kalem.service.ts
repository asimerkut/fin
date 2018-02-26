import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { HopDosyaBorcKalem } from './hop-dosya-borc-kalem.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<HopDosyaBorcKalem>;

@Injectable()
export class HopDosyaBorcKalemService {

    private resourceUrl =  SERVER_API_URL + 'api/hop-dosya-borc-kalems';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/hop-dosya-borc-kalems';

    constructor(private http: HttpClient) { }

    create(hopDosyaBorcKalem: HopDosyaBorcKalem): Observable<EntityResponseType> {
        const copy = this.convert(hopDosyaBorcKalem);
        return this.http.post<HopDosyaBorcKalem>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(hopDosyaBorcKalem: HopDosyaBorcKalem): Observable<EntityResponseType> {
        const copy = this.convert(hopDosyaBorcKalem);
        return this.http.put<HopDosyaBorcKalem>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<HopDosyaBorcKalem>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<HopDosyaBorcKalem[]>> {
        const options = createRequestOption(req);
        return this.http.get<HopDosyaBorcKalem[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<HopDosyaBorcKalem[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<HopDosyaBorcKalem[]>> {
        const options = createRequestOption(req);
        return this.http.get<HopDosyaBorcKalem[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<HopDosyaBorcKalem[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: HopDosyaBorcKalem = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<HopDosyaBorcKalem[]>): HttpResponse<HopDosyaBorcKalem[]> {
        const jsonResponse: HopDosyaBorcKalem[] = res.body;
        const body: HopDosyaBorcKalem[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to HopDosyaBorcKalem.
     */
    private convertItemFromServer(hopDosyaBorcKalem: HopDosyaBorcKalem): HopDosyaBorcKalem {
        const copy: HopDosyaBorcKalem = Object.assign({}, hopDosyaBorcKalem);
        return copy;
    }

    /**
     * Convert a HopDosyaBorcKalem to a JSON which can be sent to the server.
     */
    private convert(hopDosyaBorcKalem: HopDosyaBorcKalem): HopDosyaBorcKalem {
        const copy: HopDosyaBorcKalem = Object.assign({}, hopDosyaBorcKalem);
        return copy;
    }
}

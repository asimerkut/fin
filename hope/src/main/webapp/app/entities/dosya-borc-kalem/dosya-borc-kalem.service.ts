import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { DosyaBorcKalem } from './dosya-borc-kalem.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<DosyaBorcKalem>;

@Injectable()
export class DosyaBorcKalemService {

    private resourceUrl =  SERVER_API_URL + 'api/dosya-borc-kalems';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/dosya-borc-kalems';

    constructor(private http: HttpClient) { }

    create(dosyaBorcKalem: DosyaBorcKalem): Observable<EntityResponseType> {
        const copy = this.convert(dosyaBorcKalem);
        return this.http.post<DosyaBorcKalem>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(dosyaBorcKalem: DosyaBorcKalem): Observable<EntityResponseType> {
        const copy = this.convert(dosyaBorcKalem);
        return this.http.put<DosyaBorcKalem>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<DosyaBorcKalem>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<DosyaBorcKalem[]>> {
        const options = createRequestOption(req);
        return this.http.get<DosyaBorcKalem[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DosyaBorcKalem[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<DosyaBorcKalem[]>> {
        const options = createRequestOption(req);
        return this.http.get<DosyaBorcKalem[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DosyaBorcKalem[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: DosyaBorcKalem = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<DosyaBorcKalem[]>): HttpResponse<DosyaBorcKalem[]> {
        const jsonResponse: DosyaBorcKalem[] = res.body;
        const body: DosyaBorcKalem[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to DosyaBorcKalem.
     */
    private convertItemFromServer(dosyaBorcKalem: DosyaBorcKalem): DosyaBorcKalem {
        const copy: DosyaBorcKalem = Object.assign({}, dosyaBorcKalem);
        return copy;
    }

    /**
     * Convert a DosyaBorcKalem to a JSON which can be sent to the server.
     */
    private convert(dosyaBorcKalem: DosyaBorcKalem): DosyaBorcKalem {
        const copy: DosyaBorcKalem = Object.assign({}, dosyaBorcKalem);
        return copy;
    }
}

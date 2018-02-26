import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Dosya } from './dosya.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Dosya>;

@Injectable()
export class DosyaService {

    private resourceUrl =  SERVER_API_URL + 'api/dosyas';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/dosyas';

    constructor(private http: HttpClient) { }

    create(dosya: Dosya): Observable<EntityResponseType> {
        const copy = this.convert(dosya);
        return this.http.post<Dosya>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(dosya: Dosya): Observable<EntityResponseType> {
        const copy = this.convert(dosya);
        return this.http.put<Dosya>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Dosya>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Dosya[]>> {
        const options = createRequestOption(req);
        return this.http.get<Dosya[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Dosya[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Dosya[]>> {
        const options = createRequestOption(req);
        return this.http.get<Dosya[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Dosya[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Dosya = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Dosya[]>): HttpResponse<Dosya[]> {
        const jsonResponse: Dosya[] = res.body;
        const body: Dosya[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Dosya.
     */
    private convertItemFromServer(dosya: Dosya): Dosya {
        const copy: Dosya = Object.assign({}, dosya);
        return copy;
    }

    /**
     * Convert a Dosya to a JSON which can be sent to the server.
     */
    private convert(dosya: Dosya): Dosya {
        const copy: Dosya = Object.assign({}, dosya);
        return copy;
    }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { HopDosya } from './hop-dosya.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<HopDosya>;

@Injectable()
export class HopDosyaService {

    private resourceUrl =  SERVER_API_URL + 'api/hop-dosyas';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/hop-dosyas';

    constructor(private http: HttpClient) { }

    create(hopDosya: HopDosya): Observable<EntityResponseType> {
        const copy = this.convert(hopDosya);
        return this.http.post<HopDosya>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(hopDosya: HopDosya): Observable<EntityResponseType> {
        const copy = this.convert(hopDosya);
        return this.http.put<HopDosya>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<HopDosya>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<HopDosya[]>> {
        const options = createRequestOption(req);
        return this.http.get<HopDosya[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<HopDosya[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<HopDosya[]>> {
        const options = createRequestOption(req);
        return this.http.get<HopDosya[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<HopDosya[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: HopDosya = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<HopDosya[]>): HttpResponse<HopDosya[]> {
        const jsonResponse: HopDosya[] = res.body;
        const body: HopDosya[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to HopDosya.
     */
    private convertItemFromServer(hopDosya: HopDosya): HopDosya {
        const copy: HopDosya = Object.assign({}, hopDosya);
        return copy;
    }

    /**
     * Convert a HopDosya to a JSON which can be sent to the server.
     */
    private convert(hopDosya: HopDosya): HopDosya {
        const copy: HopDosya = Object.assign({}, hopDosya);
        return copy;
    }
}

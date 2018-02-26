import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { HopFinansalHareketDetay } from './hop-finansal-hareket-detay.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<HopFinansalHareketDetay>;

@Injectable()
export class HopFinansalHareketDetayService {

    private resourceUrl =  SERVER_API_URL + 'api/hop-finansal-hareket-detays';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/hop-finansal-hareket-detays';

    constructor(private http: HttpClient) { }

    create(hopFinansalHareketDetay: HopFinansalHareketDetay): Observable<EntityResponseType> {
        const copy = this.convert(hopFinansalHareketDetay);
        return this.http.post<HopFinansalHareketDetay>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(hopFinansalHareketDetay: HopFinansalHareketDetay): Observable<EntityResponseType> {
        const copy = this.convert(hopFinansalHareketDetay);
        return this.http.put<HopFinansalHareketDetay>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<HopFinansalHareketDetay>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<HopFinansalHareketDetay[]>> {
        const options = createRequestOption(req);
        return this.http.get<HopFinansalHareketDetay[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<HopFinansalHareketDetay[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<HopFinansalHareketDetay[]>> {
        const options = createRequestOption(req);
        return this.http.get<HopFinansalHareketDetay[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<HopFinansalHareketDetay[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: HopFinansalHareketDetay = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<HopFinansalHareketDetay[]>): HttpResponse<HopFinansalHareketDetay[]> {
        const jsonResponse: HopFinansalHareketDetay[] = res.body;
        const body: HopFinansalHareketDetay[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to HopFinansalHareketDetay.
     */
    private convertItemFromServer(hopFinansalHareketDetay: HopFinansalHareketDetay): HopFinansalHareketDetay {
        const copy: HopFinansalHareketDetay = Object.assign({}, hopFinansalHareketDetay);
        return copy;
    }

    /**
     * Convert a HopFinansalHareketDetay to a JSON which can be sent to the server.
     */
    private convert(hopFinansalHareketDetay: HopFinansalHareketDetay): HopFinansalHareketDetay {
        const copy: HopFinansalHareketDetay = Object.assign({}, hopFinansalHareketDetay);
        return copy;
    }
}

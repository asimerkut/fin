import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { FinansalHareketDetay } from './finansal-hareket-detay.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<FinansalHareketDetay>;

@Injectable()
export class FinansalHareketDetayService {

    private resourceUrl =  SERVER_API_URL + 'api/finansal-hareket-detays';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/finansal-hareket-detays';

    constructor(private http: HttpClient) { }

    create(finansalHareketDetay: FinansalHareketDetay): Observable<EntityResponseType> {
        const copy = this.convert(finansalHareketDetay);
        return this.http.post<FinansalHareketDetay>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(finansalHareketDetay: FinansalHareketDetay): Observable<EntityResponseType> {
        const copy = this.convert(finansalHareketDetay);
        return this.http.put<FinansalHareketDetay>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<FinansalHareketDetay>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<FinansalHareketDetay[]>> {
        const options = createRequestOption(req);
        return this.http.get<FinansalHareketDetay[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<FinansalHareketDetay[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<FinansalHareketDetay[]>> {
        const options = createRequestOption(req);
        return this.http.get<FinansalHareketDetay[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<FinansalHareketDetay[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: FinansalHareketDetay = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<FinansalHareketDetay[]>): HttpResponse<FinansalHareketDetay[]> {
        const jsonResponse: FinansalHareketDetay[] = res.body;
        const body: FinansalHareketDetay[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to FinansalHareketDetay.
     */
    private convertItemFromServer(finansalHareketDetay: FinansalHareketDetay): FinansalHareketDetay {
        const copy: FinansalHareketDetay = Object.assign({}, finansalHareketDetay);
        return copy;
    }

    /**
     * Convert a FinansalHareketDetay to a JSON which can be sent to the server.
     */
    private convert(finansalHareketDetay: FinansalHareketDetay): FinansalHareketDetay {
        const copy: FinansalHareketDetay = Object.assign({}, finansalHareketDetay);
        return copy;
    }
}

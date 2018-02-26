import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { HopFinansalHareket } from './hop-finansal-hareket.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<HopFinansalHareket>;

@Injectable()
export class HopFinansalHareketService {

    private resourceUrl =  SERVER_API_URL + 'api/hop-finansal-harekets';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/hop-finansal-harekets';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(hopFinansalHareket: HopFinansalHareket): Observable<EntityResponseType> {
        const copy = this.convert(hopFinansalHareket);
        return this.http.post<HopFinansalHareket>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(hopFinansalHareket: HopFinansalHareket): Observable<EntityResponseType> {
        const copy = this.convert(hopFinansalHareket);
        return this.http.put<HopFinansalHareket>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<HopFinansalHareket>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<HopFinansalHareket[]>> {
        const options = createRequestOption(req);
        return this.http.get<HopFinansalHareket[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<HopFinansalHareket[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<HopFinansalHareket[]>> {
        const options = createRequestOption(req);
        return this.http.get<HopFinansalHareket[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<HopFinansalHareket[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: HopFinansalHareket = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<HopFinansalHareket[]>): HttpResponse<HopFinansalHareket[]> {
        const jsonResponse: HopFinansalHareket[] = res.body;
        const body: HopFinansalHareket[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to HopFinansalHareket.
     */
    private convertItemFromServer(hopFinansalHareket: HopFinansalHareket): HopFinansalHareket {
        const copy: HopFinansalHareket = Object.assign({}, hopFinansalHareket);
        copy.tarih = this.dateUtils
            .convertLocalDateFromServer(hopFinansalHareket.tarih);
        return copy;
    }

    /**
     * Convert a HopFinansalHareket to a JSON which can be sent to the server.
     */
    private convert(hopFinansalHareket: HopFinansalHareket): HopFinansalHareket {
        const copy: HopFinansalHareket = Object.assign({}, hopFinansalHareket);
        copy.tarih = this.dateUtils
            .convertLocalDateToServer(hopFinansalHareket.tarih);
        return copy;
    }
}

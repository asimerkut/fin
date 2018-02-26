import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { FinansalHareket } from './finansal-hareket.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<FinansalHareket>;

@Injectable()
export class FinansalHareketService {

    private resourceUrl =  SERVER_API_URL + 'api/finansal-harekets';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/finansal-harekets';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(finansalHareket: FinansalHareket): Observable<EntityResponseType> {
        const copy = this.convert(finansalHareket);
        return this.http.post<FinansalHareket>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(finansalHareket: FinansalHareket): Observable<EntityResponseType> {
        const copy = this.convert(finansalHareket);
        return this.http.put<FinansalHareket>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<FinansalHareket>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<FinansalHareket[]>> {
        const options = createRequestOption(req);
        return this.http.get<FinansalHareket[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<FinansalHareket[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<FinansalHareket[]>> {
        const options = createRequestOption(req);
        return this.http.get<FinansalHareket[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<FinansalHareket[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: FinansalHareket = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<FinansalHareket[]>): HttpResponse<FinansalHareket[]> {
        const jsonResponse: FinansalHareket[] = res.body;
        const body: FinansalHareket[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to FinansalHareket.
     */
    private convertItemFromServer(finansalHareket: FinansalHareket): FinansalHareket {
        const copy: FinansalHareket = Object.assign({}, finansalHareket);
        copy.islemKabulTarihi = this.dateUtils
            .convertLocalDateFromServer(finansalHareket.islemKabulTarihi);
        return copy;
    }

    /**
     * Convert a FinansalHareket to a JSON which can be sent to the server.
     */
    private convert(finansalHareket: FinansalHareket): FinansalHareket {
        const copy: FinansalHareket = Object.assign({}, finansalHareket);
        copy.islemKabulTarihi = this.dateUtils
            .convertLocalDateToServer(finansalHareket.islemKabulTarihi);
        return copy;
    }
}

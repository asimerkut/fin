import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Masraf } from './masraf.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Masraf>;

@Injectable()
export class MasrafService {

    private resourceUrl =  SERVER_API_URL + 'api/masrafs';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/masrafs';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(masraf: Masraf): Observable<EntityResponseType> {
        const copy = this.convert(masraf);
        return this.http.post<Masraf>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(masraf: Masraf): Observable<EntityResponseType> {
        const copy = this.convert(masraf);
        return this.http.put<Masraf>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Masraf>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Masraf[]>> {
        const options = createRequestOption(req);
        return this.http.get<Masraf[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Masraf[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Masraf[]>> {
        const options = createRequestOption(req);
        return this.http.get<Masraf[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Masraf[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Masraf = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Masraf[]>): HttpResponse<Masraf[]> {
        const jsonResponse: Masraf[] = res.body;
        const body: Masraf[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Masraf.
     */
    private convertItemFromServer(masraf: Masraf): Masraf {
        const copy: Masraf = Object.assign({}, masraf);
        copy.masrafTarihi = this.dateUtils
            .convertLocalDateFromServer(masraf.masrafTarihi);
        return copy;
    }

    /**
     * Convert a Masraf to a JSON which can be sent to the server.
     */
    private convert(masraf: Masraf): Masraf {
        const copy: Masraf = Object.assign({}, masraf);
        copy.masrafTarihi = this.dateUtils
            .convertLocalDateToServer(masraf.masrafTarihi);
        return copy;
    }
}

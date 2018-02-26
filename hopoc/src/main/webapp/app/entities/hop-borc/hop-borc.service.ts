import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { HopBorc } from './hop-borc.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<HopBorc>;

@Injectable()
export class HopBorcService {

    private resourceUrl =  SERVER_API_URL + 'api/hop-borcs';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/hop-borcs';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(hopBorc: HopBorc): Observable<EntityResponseType> {
        const copy = this.convert(hopBorc);
        return this.http.post<HopBorc>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(hopBorc: HopBorc): Observable<EntityResponseType> {
        const copy = this.convert(hopBorc);
        return this.http.put<HopBorc>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<HopBorc>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<HopBorc[]>> {
        const options = createRequestOption(req);
        return this.http.get<HopBorc[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<HopBorc[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<HopBorc[]>> {
        const options = createRequestOption(req);
        return this.http.get<HopBorc[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<HopBorc[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: HopBorc = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<HopBorc[]>): HttpResponse<HopBorc[]> {
        const jsonResponse: HopBorc[] = res.body;
        const body: HopBorc[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to HopBorc.
     */
    private convertItemFromServer(hopBorc: HopBorc): HopBorc {
        const copy: HopBorc = Object.assign({}, hopBorc);
        copy.tarih = this.dateUtils
            .convertLocalDateFromServer(hopBorc.tarih);
        return copy;
    }

    /**
     * Convert a HopBorc to a JSON which can be sent to the server.
     */
    private convert(hopBorc: HopBorc): HopBorc {
        const copy: HopBorc = Object.assign({}, hopBorc);
        copy.tarih = this.dateUtils
            .convertLocalDateToServer(hopBorc.tarih);
        return copy;
    }
}

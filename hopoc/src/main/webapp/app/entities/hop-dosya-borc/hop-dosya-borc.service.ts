import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { HopDosyaBorc } from './hop-dosya-borc.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<HopDosyaBorc>;

@Injectable()
export class HopDosyaBorcService {

    private resourceUrl =  SERVER_API_URL + 'api/hop-dosya-borcs';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/hop-dosya-borcs';

    constructor(private http: HttpClient) { }

    create(hopDosyaBorc: HopDosyaBorc): Observable<EntityResponseType> {
        const copy = this.convert(hopDosyaBorc);
        return this.http.post<HopDosyaBorc>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(hopDosyaBorc: HopDosyaBorc): Observable<EntityResponseType> {
        const copy = this.convert(hopDosyaBorc);
        return this.http.put<HopDosyaBorc>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<HopDosyaBorc>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<HopDosyaBorc[]>> {
        const options = createRequestOption(req);
        return this.http.get<HopDosyaBorc[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<HopDosyaBorc[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<HopDosyaBorc[]>> {
        const options = createRequestOption(req);
        return this.http.get<HopDosyaBorc[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<HopDosyaBorc[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: HopDosyaBorc = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<HopDosyaBorc[]>): HttpResponse<HopDosyaBorc[]> {
        const jsonResponse: HopDosyaBorc[] = res.body;
        const body: HopDosyaBorc[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to HopDosyaBorc.
     */
    private convertItemFromServer(hopDosyaBorc: HopDosyaBorc): HopDosyaBorc {
        const copy: HopDosyaBorc = Object.assign({}, hopDosyaBorc);
        return copy;
    }

    /**
     * Convert a HopDosyaBorc to a JSON which can be sent to the server.
     */
    private convert(hopDosyaBorc: HopDosyaBorc): HopDosyaBorc {
        const copy: HopDosyaBorc = Object.assign({}, hopDosyaBorc);
        return copy;
    }
}

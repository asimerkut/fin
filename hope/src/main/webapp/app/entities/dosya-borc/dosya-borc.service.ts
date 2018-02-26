import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { DosyaBorc } from './dosya-borc.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<DosyaBorc>;

@Injectable()
export class DosyaBorcService {

    private resourceUrl =  SERVER_API_URL + 'api/dosya-borcs';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/dosya-borcs';

    constructor(private http: HttpClient) { }

    create(dosyaBorc: DosyaBorc): Observable<EntityResponseType> {
        const copy = this.convert(dosyaBorc);
        return this.http.post<DosyaBorc>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(dosyaBorc: DosyaBorc): Observable<EntityResponseType> {
        const copy = this.convert(dosyaBorc);
        return this.http.put<DosyaBorc>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<DosyaBorc>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<DosyaBorc[]>> {
        const options = createRequestOption(req);
        return this.http.get<DosyaBorc[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DosyaBorc[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<DosyaBorc[]>> {
        const options = createRequestOption(req);
        return this.http.get<DosyaBorc[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DosyaBorc[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: DosyaBorc = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<DosyaBorc[]>): HttpResponse<DosyaBorc[]> {
        const jsonResponse: DosyaBorc[] = res.body;
        const body: DosyaBorc[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to DosyaBorc.
     */
    private convertItemFromServer(dosyaBorc: DosyaBorc): DosyaBorc {
        const copy: DosyaBorc = Object.assign({}, dosyaBorc);
        return copy;
    }

    /**
     * Convert a DosyaBorc to a JSON which can be sent to the server.
     */
    private convert(dosyaBorc: DosyaBorc): DosyaBorc {
        const copy: DosyaBorc = Object.assign({}, dosyaBorc);
        return copy;
    }
}

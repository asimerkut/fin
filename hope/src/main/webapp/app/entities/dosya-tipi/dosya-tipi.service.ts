import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { DosyaTipi } from './dosya-tipi.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<DosyaTipi>;

@Injectable()
export class DosyaTipiService {

    private resourceUrl =  SERVER_API_URL + 'api/dosya-tipis';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/dosya-tipis';

    constructor(private http: HttpClient) { }

    create(dosyaTipi: DosyaTipi): Observable<EntityResponseType> {
        const copy = this.convert(dosyaTipi);
        return this.http.post<DosyaTipi>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(dosyaTipi: DosyaTipi): Observable<EntityResponseType> {
        const copy = this.convert(dosyaTipi);
        return this.http.put<DosyaTipi>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<DosyaTipi>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<DosyaTipi[]>> {
        const options = createRequestOption(req);
        return this.http.get<DosyaTipi[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DosyaTipi[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<DosyaTipi[]>> {
        const options = createRequestOption(req);
        return this.http.get<DosyaTipi[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DosyaTipi[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: DosyaTipi = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<DosyaTipi[]>): HttpResponse<DosyaTipi[]> {
        const jsonResponse: DosyaTipi[] = res.body;
        const body: DosyaTipi[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to DosyaTipi.
     */
    private convertItemFromServer(dosyaTipi: DosyaTipi): DosyaTipi {
        const copy: DosyaTipi = Object.assign({}, dosyaTipi);
        return copy;
    }

    /**
     * Convert a DosyaTipi to a JSON which can be sent to the server.
     */
    private convert(dosyaTipi: DosyaTipi): DosyaTipi {
        const copy: DosyaTipi = Object.assign({}, dosyaTipi);
        return copy;
    }
}

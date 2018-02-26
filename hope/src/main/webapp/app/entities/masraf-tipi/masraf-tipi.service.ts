import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { MasrafTipi } from './masraf-tipi.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<MasrafTipi>;

@Injectable()
export class MasrafTipiService {

    private resourceUrl =  SERVER_API_URL + 'api/masraf-tipis';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/masraf-tipis';

    constructor(private http: HttpClient) { }

    create(masrafTipi: MasrafTipi): Observable<EntityResponseType> {
        const copy = this.convert(masrafTipi);
        return this.http.post<MasrafTipi>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(masrafTipi: MasrafTipi): Observable<EntityResponseType> {
        const copy = this.convert(masrafTipi);
        return this.http.put<MasrafTipi>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<MasrafTipi>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<MasrafTipi[]>> {
        const options = createRequestOption(req);
        return this.http.get<MasrafTipi[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<MasrafTipi[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<MasrafTipi[]>> {
        const options = createRequestOption(req);
        return this.http.get<MasrafTipi[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<MasrafTipi[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: MasrafTipi = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<MasrafTipi[]>): HttpResponse<MasrafTipi[]> {
        const jsonResponse: MasrafTipi[] = res.body;
        const body: MasrafTipi[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to MasrafTipi.
     */
    private convertItemFromServer(masrafTipi: MasrafTipi): MasrafTipi {
        const copy: MasrafTipi = Object.assign({}, masrafTipi);
        return copy;
    }

    /**
     * Convert a MasrafTipi to a JSON which can be sent to the server.
     */
    private convert(masrafTipi: MasrafTipi): MasrafTipi {
        const copy: MasrafTipi = Object.assign({}, masrafTipi);
        return copy;
    }
}

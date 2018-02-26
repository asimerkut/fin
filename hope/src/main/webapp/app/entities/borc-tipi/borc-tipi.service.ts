import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { BorcTipi } from './borc-tipi.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<BorcTipi>;

@Injectable()
export class BorcTipiService {

    private resourceUrl =  SERVER_API_URL + 'api/borc-tipis';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/borc-tipis';

    constructor(private http: HttpClient) { }

    create(borcTipi: BorcTipi): Observable<EntityResponseType> {
        const copy = this.convert(borcTipi);
        return this.http.post<BorcTipi>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(borcTipi: BorcTipi): Observable<EntityResponseType> {
        const copy = this.convert(borcTipi);
        return this.http.put<BorcTipi>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<BorcTipi>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<BorcTipi[]>> {
        const options = createRequestOption(req);
        return this.http.get<BorcTipi[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<BorcTipi[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<BorcTipi[]>> {
        const options = createRequestOption(req);
        return this.http.get<BorcTipi[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<BorcTipi[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: BorcTipi = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<BorcTipi[]>): HttpResponse<BorcTipi[]> {
        const jsonResponse: BorcTipi[] = res.body;
        const body: BorcTipi[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to BorcTipi.
     */
    private convertItemFromServer(borcTipi: BorcTipi): BorcTipi {
        const copy: BorcTipi = Object.assign({}, borcTipi);
        return copy;
    }

    /**
     * Convert a BorcTipi to a JSON which can be sent to the server.
     */
    private convert(borcTipi: BorcTipi): BorcTipi {
        const copy: BorcTipi = Object.assign({}, borcTipi);
        return copy;
    }
}

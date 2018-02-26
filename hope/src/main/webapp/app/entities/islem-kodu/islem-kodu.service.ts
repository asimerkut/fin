import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { IslemKodu } from './islem-kodu.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<IslemKodu>;

@Injectable()
export class IslemKoduService {

    private resourceUrl =  SERVER_API_URL + 'api/islem-kodus';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/islem-kodus';

    constructor(private http: HttpClient) { }

    create(islemKodu: IslemKodu): Observable<EntityResponseType> {
        const copy = this.convert(islemKodu);
        return this.http.post<IslemKodu>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(islemKodu: IslemKodu): Observable<EntityResponseType> {
        const copy = this.convert(islemKodu);
        return this.http.put<IslemKodu>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IslemKodu>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<IslemKodu[]>> {
        const options = createRequestOption(req);
        return this.http.get<IslemKodu[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<IslemKodu[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<IslemKodu[]>> {
        const options = createRequestOption(req);
        return this.http.get<IslemKodu[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<IslemKodu[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: IslemKodu = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<IslemKodu[]>): HttpResponse<IslemKodu[]> {
        const jsonResponse: IslemKodu[] = res.body;
        const body: IslemKodu[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to IslemKodu.
     */
    private convertItemFromServer(islemKodu: IslemKodu): IslemKodu {
        const copy: IslemKodu = Object.assign({}, islemKodu);
        return copy;
    }

    /**
     * Convert a IslemKodu to a JSON which can be sent to the server.
     */
    private convert(islemKodu: IslemKodu): IslemKodu {
        const copy: IslemKodu = Object.assign({}, islemKodu);
        return copy;
    }
}

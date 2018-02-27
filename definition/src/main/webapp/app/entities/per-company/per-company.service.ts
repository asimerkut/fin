import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { PerCompany } from './per-company.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<PerCompany>;

@Injectable()
export class PerCompanyService {

    private resourceUrl =  SERVER_API_URL + 'api/per-companies';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/per-companies';

    constructor(private http: HttpClient) { }

    create(perCompany: PerCompany): Observable<EntityResponseType> {
        const copy = this.convert(perCompany);
        return this.http.post<PerCompany>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(perCompany: PerCompany): Observable<EntityResponseType> {
        const copy = this.convert(perCompany);
        return this.http.put<PerCompany>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<PerCompany>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<PerCompany[]>> {
        const options = createRequestOption(req);
        return this.http.get<PerCompany[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PerCompany[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<PerCompany[]>> {
        const options = createRequestOption(req);
        return this.http.get<PerCompany[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<PerCompany[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: PerCompany = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<PerCompany[]>): HttpResponse<PerCompany[]> {
        const jsonResponse: PerCompany[] = res.body;
        const body: PerCompany[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to PerCompany.
     */
    private convertItemFromServer(perCompany: PerCompany): PerCompany {
        const copy: PerCompany = Object.assign({}, perCompany);
        return copy;
    }

    /**
     * Convert a PerCompany to a JSON which can be sent to the server.
     */
    private convert(perCompany: PerCompany): PerCompany {
        const copy: PerCompany = Object.assign({}, perCompany);
        return copy;
    }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { BorcGrubu } from './borc-grubu.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<BorcGrubu>;

@Injectable()
export class BorcGrubuService {

    private resourceUrl =  SERVER_API_URL + 'api/borc-grubus';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/borc-grubus';

    constructor(private http: HttpClient) { }

    create(borcGrubu: BorcGrubu): Observable<EntityResponseType> {
        const copy = this.convert(borcGrubu);
        return this.http.post<BorcGrubu>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(borcGrubu: BorcGrubu): Observable<EntityResponseType> {
        const copy = this.convert(borcGrubu);
        return this.http.put<BorcGrubu>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<BorcGrubu>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<BorcGrubu[]>> {
        const options = createRequestOption(req);
        return this.http.get<BorcGrubu[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<BorcGrubu[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<BorcGrubu[]>> {
        const options = createRequestOption(req);
        return this.http.get<BorcGrubu[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<BorcGrubu[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: BorcGrubu = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<BorcGrubu[]>): HttpResponse<BorcGrubu[]> {
        const jsonResponse: BorcGrubu[] = res.body;
        const body: BorcGrubu[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to BorcGrubu.
     */
    private convertItemFromServer(borcGrubu: BorcGrubu): BorcGrubu {
        const copy: BorcGrubu = Object.assign({}, borcGrubu);
        return copy;
    }

    /**
     * Convert a BorcGrubu to a JSON which can be sent to the server.
     */
    private convert(borcGrubu: BorcGrubu): BorcGrubu {
        const copy: BorcGrubu = Object.assign({}, borcGrubu);
        return copy;
    }
}

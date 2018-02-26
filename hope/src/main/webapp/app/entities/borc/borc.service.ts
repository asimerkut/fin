import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Borc } from './borc.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Borc>;

@Injectable()
export class BorcService {

    private resourceUrl =  SERVER_API_URL + 'api/borcs';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/borcs';

    constructor(private http: HttpClient) { }

    create(borc: Borc): Observable<EntityResponseType> {
        const copy = this.convert(borc);
        return this.http.post<Borc>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(borc: Borc): Observable<EntityResponseType> {
        const copy = this.convert(borc);
        return this.http.put<Borc>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Borc>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Borc[]>> {
        const options = createRequestOption(req);
        return this.http.get<Borc[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Borc[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Borc[]>> {
        const options = createRequestOption(req);
        return this.http.get<Borc[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Borc[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Borc = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Borc[]>): HttpResponse<Borc[]> {
        const jsonResponse: Borc[] = res.body;
        const body: Borc[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Borc.
     */
    private convertItemFromServer(borc: Borc): Borc {
        const copy: Borc = Object.assign({}, borc);
        return copy;
    }

    /**
     * Convert a Borc to a JSON which can be sent to the server.
     */
    private convert(borc: Borc): Borc {
        const copy: Borc = Object.assign({}, borc);
        return copy;
    }
}

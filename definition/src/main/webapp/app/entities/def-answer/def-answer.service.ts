import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { DefAnswer } from './def-answer.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<DefAnswer>;

@Injectable()
export class DefAnswerService {

    private resourceUrl =  SERVER_API_URL + 'api/def-answers';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/def-answers';

    constructor(private http: HttpClient) { }

    create(defAnswer: DefAnswer): Observable<EntityResponseType> {
        const copy = this.convert(defAnswer);
        return this.http.post<DefAnswer>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(defAnswer: DefAnswer): Observable<EntityResponseType> {
        const copy = this.convert(defAnswer);
        return this.http.put<DefAnswer>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<DefAnswer>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<DefAnswer[]>> {
        const options = createRequestOption(req);
        return this.http.get<DefAnswer[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DefAnswer[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<DefAnswer[]>> {
        const options = createRequestOption(req);
        return this.http.get<DefAnswer[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<DefAnswer[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: DefAnswer = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<DefAnswer[]>): HttpResponse<DefAnswer[]> {
        const jsonResponse: DefAnswer[] = res.body;
        const body: DefAnswer[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to DefAnswer.
     */
    private convertItemFromServer(defAnswer: DefAnswer): DefAnswer {
        const copy: DefAnswer = Object.assign({}, defAnswer);
        return copy;
    }

    /**
     * Convert a DefAnswer to a JSON which can be sent to the server.
     */
    private convert(defAnswer: DefAnswer): DefAnswer {
        const copy: DefAnswer = Object.assign({}, defAnswer);
        return copy;
    }
}

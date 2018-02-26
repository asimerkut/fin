import { Injectable } from '@angular/core';
// import { Http, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs';

import { EntityAuditEvent } from './entity-audit-event.model';

@Injectable()
export class EntityAuditService {

    // constructor(private http: Http) { }

    getAllAudited(): Observable<string[]> {
        return null;
        // return this.http.get('api/audits/entity/all')
        //    .map((response) => response.json());
    }

    findByEntity(entity: string, limit: number): Observable<EntityAuditEvent[]> {
        return null;
        // const params = new URLSearchParams();
        // params.set('entityType', entity);
        // params.set('limit', limit.toString());

        // return this.http.get('api/audits/entity/changes', { search: params })
        //    .map((response) => response.json());
    }

    getPrevVersion(qualifiedName: string, entityId: string, commitVersion: number) {
        return null;
        // const params = new URLSearchParams();
        // params.set('qualifiedName', qualifiedName);
        // params.set('entityId', entityId);
        // params.set('commitVersion', commitVersion.toString());

        // return this.http
        //    .get('api/audits/entity/changes/version/previous', {search: params})
        //    .map((response) => response.json());
    }
}

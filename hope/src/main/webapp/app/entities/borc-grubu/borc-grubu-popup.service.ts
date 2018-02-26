import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { BorcGrubu } from './borc-grubu.model';
import { BorcGrubuService } from './borc-grubu.service';

@Injectable()
export class BorcGrubuPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private borcGrubuService: BorcGrubuService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.borcGrubuService.find(id)
                    .subscribe((borcGrubuResponse: HttpResponse<BorcGrubu>) => {
                        const borcGrubu: BorcGrubu = borcGrubuResponse.body;
                        this.ngbModalRef = this.borcGrubuModalRef(component, borcGrubu);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.borcGrubuModalRef(component, new BorcGrubu());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    borcGrubuModalRef(component: Component, borcGrubu: BorcGrubu): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.borcGrubu = borcGrubu;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}

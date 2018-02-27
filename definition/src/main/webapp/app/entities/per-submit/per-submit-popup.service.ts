import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { PerSubmit } from './per-submit.model';
import { PerSubmitService } from './per-submit.service';

@Injectable()
export class PerSubmitPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private perSubmitService: PerSubmitService

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
                this.perSubmitService.find(id)
                    .subscribe((perSubmitResponse: HttpResponse<PerSubmit>) => {
                        const perSubmit: PerSubmit = perSubmitResponse.body;
                        if (perSubmit.submitDate) {
                            perSubmit.submitDate = {
                                year: perSubmit.submitDate.getFullYear(),
                                month: perSubmit.submitDate.getMonth() + 1,
                                day: perSubmit.submitDate.getDate()
                            };
                        }
                        this.ngbModalRef = this.perSubmitModalRef(component, perSubmit);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.perSubmitModalRef(component, new PerSubmit());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    perSubmitModalRef(component: Component, perSubmit: PerSubmit): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.perSubmit = perSubmit;
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

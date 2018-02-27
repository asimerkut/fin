import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { PerExcuse } from './per-excuse.model';
import { PerExcuseService } from './per-excuse.service';

@Injectable()
export class PerExcusePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private perExcuseService: PerExcuseService

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
                this.perExcuseService.find(id)
                    .subscribe((perExcuseResponse: HttpResponse<PerExcuse>) => {
                        const perExcuse: PerExcuse = perExcuseResponse.body;
                        if (perExcuse.startDate) {
                            perExcuse.startDate = {
                                year: perExcuse.startDate.getFullYear(),
                                month: perExcuse.startDate.getMonth() + 1,
                                day: perExcuse.startDate.getDate()
                            };
                        }
                        if (perExcuse.finishDate) {
                            perExcuse.finishDate = {
                                year: perExcuse.finishDate.getFullYear(),
                                month: perExcuse.finishDate.getMonth() + 1,
                                day: perExcuse.finishDate.getDate()
                            };
                        }
                        this.ngbModalRef = this.perExcuseModalRef(component, perExcuse);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.perExcuseModalRef(component, new PerExcuse());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    perExcuseModalRef(component: Component, perExcuse: PerExcuse): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.perExcuse = perExcuse;
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

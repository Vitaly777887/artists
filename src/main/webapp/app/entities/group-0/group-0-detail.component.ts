import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGroup0 } from 'app/shared/model/group-0.model';

@Component({
    selector: 'jhi-group-0-detail',
    templateUrl: './group-0-detail.component.html'
})
export class Group0DetailComponent implements OnInit {
    group0: IGroup0;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ group0 }) => {
            this.group0 = group0;
        });
    }

    previousState() {
        window.history.back();
    }
}

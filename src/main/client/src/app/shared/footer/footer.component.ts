import { Component, OnInit } from '@angular/core';
import {environment} from '../../../environments/environment';

@Component({
  selector: 'footer-hpo',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {
  title: string;
  version = `hpo-web@${environment.VERSION} - hpo-obo@2020-10-12`;

  constructor() {
    this.title = 'Human Phenotype Ontology';
   }

  ngOnInit() {
  }

}

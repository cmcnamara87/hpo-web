import { Component, OnInit, ViewChild } from '@angular/core';
import { PublicationsService } from './publications.service';
import { Publication} from '../../../browser/models/models';
import { FormControl } from '@angular/forms';
import { from } from 'rxjs';
import { distinct, map, toArray, flatMap } from 'rxjs/operators';
import { MatSort, MatTableDataSource } from '@angular/material';


@Component({
  selector: 'app-publications',
  templateUrl: './publications.component.html',
  styleUrls: ['./publications.component.scss']
})
export class PublicationsComponent implements OnInit {

  publications: MatTableDataSource<Publication>;
  constructor(private pubService: PublicationsService) { }
  displayedColumns: string[] = ['year', 'title', 'journal', 'authors'];
  category = 'all';
  topics = new FormControl();
  topicList = [];
  @ViewChild(MatSort) sort: MatSort;

  ngOnInit() {
    this.pubService.getPublications().subscribe(publications => {
      this.publications =  new MatTableDataSource(publications);
      this.publications.sort = this.sort;
      this.publications.filterPredicate = this.filterPredicate;
      this.findUniquePublicationTopics(publications);
    });
  }

  findUniquePublicationTopics(pbs: Publication[]) {
    const topics$ = from(pbs);
    topics$.pipe(
      flatMap(pb => pb.topicList),
      distinct(),
      map(pb => pb.toUpperCase()),
      toArray()
    ).subscribe(publications => {
      this.topicList = publications;
    });
  }

  filterPredicate(data: Publication, filter: string) {
    const filterVal = filter.split('~');
    const matchesCategory = function(item: Publication, category: string) {
      return (category === 'all' || (category === 'us' && item.inhouse === true));
    };

    // Filter by category first
    const categoryBoolean = matchesCategory(data, filterVal[0]);

    if (categoryBoolean === false) {
      return false;
    }

    // Filter by tag
    const topicSize = filterVal[1].split(',')[0] === '' || filterVal[1].split(',')[0] === 'null'  ? 0 : filterVal[1].split(',').length;
    const topicBool = data.topicList.map(topic => {
      return filterVal[1].includes(topic.toUpperCase());
    });

    if (topicSize < data.topicList.length) {
      return topicBool.filter(x => x).length === topicSize;
    } else if (topicSize === data.topicList.length) {
      return topicBool.every(x => x === true);
    } else if (topicSize === 0) {
      return true;
    }
    return false;
  }

  applyPublicationsFilter() {
    this.publications.filter = this.category + '~' + this.topics.value;
  }

}

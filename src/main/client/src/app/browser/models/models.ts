export interface Gene {
    entrezGeneSymbol: string;
    entrezGeneId: number;
    dbDiseases?: Disease[];
    hpoTermName?: Array<string>;
    hpoTermId?: object;
    matchingString?: string;
}
export interface Disease {
    db?: string;
    dbId?: string;
    diseaseId?: string;
    diseaseName?: string;
    dbGenes?: Gene[];
    dbObjectId?: string;
    dbName?: string;
    qualifier?: string;
    hpoId?: string;
    dbReference?: string;
    evidenceDescription?: string;
    onsetModifier?: string;
    frequencyModifier?: string;
    with?: string;
    aspect?: string;
    synonym?: string;
    date?: string;
    assignedBy?: string;
    description?: string;
    matchingString?: string;
}
export class EntrezGene {
    uid?: string;
    name?: string;
    maplocation?: string;
    summary?: string;
    otheraliases?: string;
    aliases?: string[];
    constructor() {
      this.otheraliases = '';
      this.aliases = [];
    }
}
export interface Term {
    name?: string;
    ontologyId?: string;
    id?: string;
    definition?: string;
    altTermIds?: Array<string>;
    comment?: string;
    synonyms?: Array<string>;
    isObsolete?: boolean;
    xrefs?: Array<string>;
    purl?: string;
    childrenCount?: number;
    synonym?: string;
    matchingString?: string;
    treeCountWidth?: number;
    treeMargin?: number;
    pubmedXrefs: Array<any>;
}

export interface TermTree {
  parents: Term[];
  termCount: number;
  children: Term[];
  maxTermWidth?: number;
}
export interface News {
  title: string;
  body: string;
  date: string;
  teaserTitle: string;
  monthYear?: string;
}
export interface Contributors {
  lastName: string;
  firstName: string;
  location: string;
}

export interface TermCategory {
  catLabel: string;
  annotationCount: number;
  termSource: any;
}

export interface DialogData {
  association: string;
  term: string;
  type: string;
  counts: {genes?: number, diseases?: number, terms?: number};
}

export interface LoincEntry {
  id: string;
  longName: string;
}

export interface Publication {
  authors: string;
  title: string;
  journal: string;
  year: Number;
  volume: string;
  pages: string;
  pmid: string;
  inhouse: boolean;
  hpo: boolean;
  monarch: boolean;
  topicList: string[];
}

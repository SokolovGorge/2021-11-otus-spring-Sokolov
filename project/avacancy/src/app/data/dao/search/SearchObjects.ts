export class CategorySearchValues{
    title: string = null;
}

export class PrioritySearchValues{
    title: string = null;
}

export class TaskSearchValues{
    text: string = null;
    categoryId: number = null;
    completed: number = null;
    priorityId: number = null;
    pageNumber = 0;
    pageSize = 5;

    sortColumn = 'title';
    sortDirection = 'asc';

}

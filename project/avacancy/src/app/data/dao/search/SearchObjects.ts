export class TaskSearchValues {
    title: string = null;
}

export class VacancySearchValues {
    taskId: number;
    text: string = null;
    pageNumber = 0;
    pageSize = 10;

    sortColumn = 'title';
    sortDirection = 'asc';

}

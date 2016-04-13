'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('status_event', {
                parent: 'app',
                url: '/status_events',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Status_events'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/status_event/status_events.html',
                        controller: 'Status_eventController'
                    }
                },
                resolve: {
                }
            })
            .state('status_event.detail', {
                parent: 'app',
                url: '/status_event/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Status_event'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/status_event/status_event-detail.html',
                        controller: 'Status_eventDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Status_event', function($stateParams, Status_event) {
                        return Status_event.get({id : $stateParams.id});
                    }]
                }
            })
            .state('status_event.new', {
                parent: 'status_event',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/status_event/status_event-dialog.html',
                        controller: 'Status_eventDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    decription: null,
                                    create_date: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('status_event', null, { reload: true });
                    }, function() {
                        $state.go('status_event');
                    })
                }]
            })
            .state('status_event.edit', {
                parent: 'status_event',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/status_event/status_event-dialog.html',
                        controller: 'Status_eventDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Status_event', function(Status_event) {
                                return Status_event.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('status_event', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('status_event.delete', {
                parent: 'status_event',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/status_event/status_event-delete-dialog.html',
                        controller: 'Status_eventDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Status_event', function(Status_event) {
                                return Status_event.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('status_event', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

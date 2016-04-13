'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('status_reservation', {
                parent: 'app',
                url: '/status_reservations',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Status_reservations'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/status_reservation/status_reservations.html',
                        controller: 'Status_reservationController'
                    }
                },
                resolve: {
                }
            })
            .state('status_reservation.detail', {
                parent: 'app',
                url: '/status_reservation/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Status_reservation'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/status_reservation/status_reservation-detail.html',
                        controller: 'Status_reservationDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Status_reservation', function($stateParams, Status_reservation) {
                        return Status_reservation.get({id : $stateParams.id});
                    }]
                }
            })
            .state('status_reservation.new', {
                parent: 'status_reservation',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/status_reservation/status_reservation-dialog.html',
                        controller: 'Status_reservationDialogController',
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
                        $state.go('status_reservation', null, { reload: true });
                    }, function() {
                        $state.go('status_reservation');
                    })
                }]
            })
            .state('status_reservation.edit', {
                parent: 'status_reservation',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/status_reservation/status_reservation-dialog.html',
                        controller: 'Status_reservationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Status_reservation', function(Status_reservation) {
                                return Status_reservation.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('status_reservation', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('status_reservation.delete', {
                parent: 'status_reservation',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/status_reservation/status_reservation-delete-dialog.html',
                        controller: 'Status_reservationDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Status_reservation', function(Status_reservation) {
                                return Status_reservation.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('status_reservation', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

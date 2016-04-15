'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('reservation', {
                parent: 'app',
                url: '/reservations',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Reservations'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/reservation/reservations.html',
                        controller: 'ReservationController'
                    }
                },
                resolve: {
                }
            })
            .state('reservation.detail', {
                parent: 'app',
                url: '/reservation/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Reservation'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/reservation/reservation-detail.html',
                        controller: 'ReservationDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Reservation', function($stateParams, Reservation) {
                        return Reservation.get({id : $stateParams.id});
                    }]
                }
            })
            .state('reservation.new', {
                parent: 'reservation',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/reservation/reservation-dialog.html',
                        controller: 'ReservationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    time_checkin: new Date(),
                                    time_checkout: null,
                                    note_checkin: null,
                                    note_checkout: null,
                                    create_date: null,
                                    last_modified_date: null,
                                    id: null
                                };
                            },
                            deps: ['$ocLazyLoad',
                                   function( $ocLazyLoad ){
                                     return $ocLazyLoad.load('ui.select').then(
                                         function(){
                                             return $ocLazyLoad.load(['']);
                                         }
                                     );
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('reservation', null, { reload: true });
                    }, function() {
                        $state.go('reservation');
                    })
                }]
            })
            .state('reservation.edit', {
                parent: 'reservation',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/reservation/reservation-dialog-check-out.html',
                        controller: 'ReservationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Reservation', function(Reservation) {
                            	return Reservation.get({id : $stateParams.id});
                            }],
                            deps: ['$ocLazyLoad',
                                   function( $ocLazyLoad ){
                                     return $ocLazyLoad.load('ui.select').then(
                                         function(){
                                             return $ocLazyLoad.load(['']);
                                         }
                                     );
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('reservation', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('reservation.delete', {
                parent: 'reservation',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/reservation/reservation-delete-dialog.html',
                        controller: 'ReservationDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Reservation', function(Reservation) {
                                return Reservation.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('reservation', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('reservation.printer', {
                parent: 'reservation',
                url: '/{id}/printer',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/reservation/reservation-dialog-printer.html',
                        controller: 'ReservationDialogPrinterController',
                        size: 'lg',
                        resolve: {
                            entity: ['Reservation', function(Reservation) {
                            	return Reservation.get({id : $stateParams.id});
                            }],
                        }
                    }).result.then(function(result) {
                        $state.go('reservation', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

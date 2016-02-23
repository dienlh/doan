'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('method_booking', {
                parent: 'entity',
                url: '/method_bookings',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Method_bookings'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/method_booking/method_bookings.html',
                        controller: 'Method_bookingController'
                    }
                },
                resolve: {
                }
            })
            .state('method_booking.detail', {
                parent: 'entity',
                url: '/method_booking/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Method_booking'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/method_booking/method_booking-detail.html',
                        controller: 'Method_bookingDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Method_booking', function($stateParams, Method_booking) {
                        return Method_booking.get({id : $stateParams.id});
                    }]
                }
            })
            .state('method_booking.new', {
                parent: 'method_booking',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/method_booking/method_booking-dialog.html',
                        controller: 'Method_bookingDialogController',
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
                        $state.go('method_booking', null, { reload: true });
                    }, function() {
                        $state.go('method_booking');
                    })
                }]
            })
            .state('method_booking.edit', {
                parent: 'method_booking',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/method_booking/method_booking-dialog.html',
                        controller: 'Method_bookingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Method_booking', function(Method_booking) {
                                return Method_booking.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('method_booking', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('method_booking.delete', {
                parent: 'method_booking',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/method_booking/method_booking-delete-dialog.html',
                        controller: 'Method_bookingDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Method_booking', function(Method_booking) {
                                return Method_booking.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('method_booking', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

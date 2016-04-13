'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('status_payment', {
                parent: 'app',
                url: '/status_payments',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Status_payments'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/status_payment/status_payments.html',
                        controller: 'Status_paymentController'
                    }
                },
                resolve: {
                }
            })
            .state('status_payment.detail', {
                parent: 'app',
                url: '/status_payment/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Status_payment'
                },
                views: {
                    '': {
                        templateUrl: 'scripts/app/entities/status_payment/status_payment-detail.html',
                        controller: 'Status_paymentDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Status_payment', function($stateParams, Status_payment) {
                        return Status_payment.get({id : $stateParams.id});
                    }]
                }
            })
            .state('status_payment.new', {
                parent: 'status_payment',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/status_payment/status_payment-dialog.html',
                        controller: 'Status_paymentDialogController',
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
                        $state.go('status_payment', null, { reload: true });
                    }, function() {
                        $state.go('status_payment');
                    })
                }]
            })
            .state('status_payment.edit', {
                parent: 'status_payment',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/status_payment/status_payment-dialog.html',
                        controller: 'Status_paymentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Status_payment', function(Status_payment) {
                                return Status_payment.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('status_payment', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('status_payment.delete', {
                parent: 'status_payment',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/status_payment/status_payment-delete-dialog.html',
                        controller: 'Status_paymentDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Status_payment', function(Status_payment) {
                                return Status_payment.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('status_payment', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

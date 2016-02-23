'use strict';

angular.module('hotelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('method_payment', {
                parent: 'entity',
                url: '/method_payments',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Method_payments'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/method_payment/method_payments.html',
                        controller: 'Method_paymentController'
                    }
                },
                resolve: {
                }
            })
            .state('method_payment.detail', {
                parent: 'entity',
                url: '/method_payment/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Method_payment'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/method_payment/method_payment-detail.html',
                        controller: 'Method_paymentDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Method_payment', function($stateParams, Method_payment) {
                        return Method_payment.get({id : $stateParams.id});
                    }]
                }
            })
            .state('method_payment.new', {
                parent: 'method_payment',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/method_payment/method_payment-dialog.html',
                        controller: 'Method_paymentDialogController',
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
                        $state.go('method_payment', null, { reload: true });
                    }, function() {
                        $state.go('method_payment');
                    })
                }]
            })
            .state('method_payment.edit', {
                parent: 'method_payment',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/method_payment/method_payment-dialog.html',
                        controller: 'Method_paymentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Method_payment', function(Method_payment) {
                                return Method_payment.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('method_payment', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('method_payment.delete', {
                parent: 'method_payment',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/method_payment/method_payment-delete-dialog.html',
                        controller: 'Method_paymentDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Method_payment', function(Method_payment) {
                                return Method_payment.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('method_payment', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });

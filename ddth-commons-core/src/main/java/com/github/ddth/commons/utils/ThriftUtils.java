package com.github.ddth.commons.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.TimeUnit;

import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.server.TThreadedSelectorServer.Args.AcceptPolicy;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TIOStreamTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.TTransportFactory;

/**
 * Thrift-related utilities.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.4.0
 */
public class ThriftUtils {
    private static TProtocolFactory protocolFactory = new TCompactProtocol.Factory();

    /**
     * Serializes a thrift object to byte array.
     * 
     * @param record
     * @return
     * @throws TException
     */
    public static byte[] toBytes(TBase<?, ?> record) throws TException {
        if (record == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        TTransport transport = new TIOStreamTransport(null, baos);
        TProtocol oProtocol = protocolFactory.getProtocol(transport);
        record.write(oProtocol);
        // baos.close();
        return baos.toByteArray();
    }

    /**
     * Deserializes a thrift object from byte array.
     * 
     * @param data
     * @param clazz
     * @return
     * @throws TException
     */
    public static <T extends TBase<?, ?>> T fromBytes(byte[] data, Class<T> clazz)
            throws TException {
        if (data == null) {
            return null;
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        try {
            TTransport transport = new TIOStreamTransport(bais, null);
            TProtocol iProtocol = protocolFactory.getProtocol(transport);
            T record = clazz.newInstance();
            record.read(iProtocol);
            // bais.close();
            return record;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new TException(e);
        }
    }

    /**
     * Helper method to create a new framed-transport, threaded-{@link TServer}.
     * 
     * <p>
     * Note: if {@code maxWorkerThreads < 1}, the {@link TServer} is created
     * with {@code maxWorkerThreads} =
     * {@code Math.max(2, Runtime.getRuntime().availableProcessors())}
     * </p>
     * 
     * @param processorFactory
     * @param protocolFactory
     * @param port
     * @param maxWorkerThreads
     * @param clientTimeoutMillisecs
     * @param maxFrameSize
     * @return
     * @throws TTransportException
     */
    public static TServer createThreadedServer(TProcessorFactory processorFactory,
            TProtocolFactory protocolFactory, int port, int maxWorkerThreads,
            int clientTimeoutMillisecs, int maxFrameSize) throws TTransportException {
        if (maxWorkerThreads < 1) {
            maxWorkerThreads = Math.max(2, Runtime.getRuntime().availableProcessors());
        }
        TServerTransport transport = new TServerSocket(port, clientTimeoutMillisecs);
        TTransportFactory transportFactory = new TFramedTransport.Factory(maxFrameSize);
        TThreadPoolServer.Args args = new TThreadPoolServer.Args(transport)
                .processorFactory(processorFactory).protocolFactory(protocolFactory)
                .transportFactory(transportFactory).minWorkerThreads(1)
                .maxWorkerThreads(maxWorkerThreads);
        TThreadPoolServer server = new TThreadPoolServer(args);
        return server;
    }

    /**
     * Helper method to create a new framed-transport, non-blocking
     * {@link TServer}.
     * 
     * @param processorFactory
     * @param protocolFactory
     * @param port
     * @param clientTimeoutMillisecs
     * @param maxFrameSize
     * @param maxReadBufferSize
     * @return
     * @throws TTransportException
     */
    public static TServer createNonBlockingServer(TProcessorFactory processorFactory,
            TProtocolFactory protocolFactory, int port, int clientTimeoutMillisecs,
            int maxFrameSize, long maxReadBufferSize) throws TTransportException {
        TNonblockingServerTransport transport = new TNonblockingServerSocket(port,
                clientTimeoutMillisecs);
        TTransportFactory transportFactory = new TFramedTransport.Factory(maxFrameSize);
        TNonblockingServer.Args args = new TNonblockingServer.Args(transport)
                .processorFactory(processorFactory).protocolFactory(protocolFactory)
                .transportFactory(transportFactory);
        args.maxReadBufferBytes = maxReadBufferSize;
        TNonblockingServer server = new TNonblockingServer(args);
        return server;
    }

    /**
     * Helper method to create a new framed-transport, HsHa {@link TServer}.
     * 
     * <p>
     * Note: if {@code maxWorkerThreads < 1}, the {@link TServer} is created
     * with {@code maxWorkerThreads} =
     * {@code Math.max(2, Runtime.getRuntime().availableProcessors())}
     * </p>
     * 
     * @param processorFactory
     * @param protocolFactory
     * @param port
     * @param maxWorkerThreads
     * @param clientTimeoutMillisecs
     * @param maxFrameSize
     * @param maxReadBufferSize
     * @return
     * @throws TTransportException
     */
    public static TServer createHsHaServer(TProcessorFactory processorFactory,
            TProtocolFactory protocolFactory, int port, int maxWorkerThreads,
            int clientTimeoutMillisecs, int maxFrameSize, long maxReadBufferSize)
            throws TTransportException {
        if (maxWorkerThreads < 1) {
            maxWorkerThreads = Math.max(2, Runtime.getRuntime().availableProcessors());
        }
        TNonblockingServerTransport transport = new TNonblockingServerSocket(port,
                clientTimeoutMillisecs);
        TTransportFactory transportFactory = new TFramedTransport.Factory(maxFrameSize);
        THsHaServer.Args args = new THsHaServer.Args(transport).processorFactory(processorFactory)
                .protocolFactory(protocolFactory).transportFactory(transportFactory)
                .minWorkerThreads(1).maxWorkerThreads(maxWorkerThreads).stopTimeoutVal(60)
                .stopTimeoutUnit(TimeUnit.SECONDS);
        args.maxReadBufferBytes = maxReadBufferSize;
        THsHaServer server = new THsHaServer(args);
        return server;
    }

    /**
     * Helper method to create a new framed-transport, threaded-selector
     * {@link TServer}.
     * 
     * <p>
     * Note: if {@code numSelectorThreads < 1}, the {@link TServer} is created
     * with 2 selector threads.
     * </p>
     * 
     * <p>
     * Note: if {@code numWorkerThreads < 1}, the {@link TServer} is created
     * with 8 worker threads.
     * </p>
     * 
     * @param processorFactory
     * @param protocolFactory
     * @param port
     * @param numSelectorThreads
     * @param numWorkerThreads
     * @param clientTimeoutMillisecs
     * @param maxFrameSize
     * @param maxReadBufferSize
     * @return
     * @throws TTransportException
     */
    public static TServer createThreadedSelectorServer(TProcessorFactory processorFactory,
            TProtocolFactory protocolFactory, int port, int numSelectorThreads,
            int numWorkerThreads, int clientTimeoutMillisecs, int maxFrameSize,
            long maxReadBufferSize) throws TTransportException {
        if (numSelectorThreads < 1) {
            numSelectorThreads = 2;
        }
        if (numWorkerThreads < 1) {
            numWorkerThreads = 8;
        }
        TNonblockingServerTransport transport = new TNonblockingServerSocket(port,
                clientTimeoutMillisecs);
        TTransportFactory transportFactory = new TFramedTransport.Factory(maxFrameSize);
        TThreadedSelectorServer.Args args = new TThreadedSelectorServer.Args(transport)
                .processorFactory(processorFactory).protocolFactory(protocolFactory)
                .transportFactory(transportFactory).workerThreads(numWorkerThreads)
                .acceptPolicy(AcceptPolicy.FAIR_ACCEPT).acceptQueueSizePerThread(10000)
                .selectorThreads(numSelectorThreads);
        args.maxReadBufferBytes = maxReadBufferSize;
        TThreadedSelectorServer server = new TThreadedSelectorServer(args);
        return server;
    }
}

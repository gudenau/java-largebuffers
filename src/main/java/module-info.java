module net.gudenau.lib.LargeBuffers {
    requires net.gudenau.lib.Annotations;
    requires net.gudenau.lib.Cleanup;
    
    exports net.gudenau.lib.largebuffers;
    
    exports net.gudenau.lib.largebuffers.implementation to net.gudenau.lib.JIT;
}
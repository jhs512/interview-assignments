@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @PostMapping
    public ResponseEntity<Post> addPost(@RequestBody Post post) {
        post.setUpdatedAt(LocalDateTime.now());
        Post savedPost = postRepository.save(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id) {
        return postRepository.findById(id)
                .map(post -> ResponseEntity.ok(post))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post updatedPost) {
        return postRepository.findById(id)
                .map(post -> {
                    post.setTitle(updatedPost.getTitle());
                    post.setContent(updatedPost.getContent());
                    post.setUpdatedAt(LocalDateTime.now());
                    postRepository.save(post);
                    return ResponseEntity.ok(post);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
